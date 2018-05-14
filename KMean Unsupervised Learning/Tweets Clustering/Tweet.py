import json
import re
import sys
global count

stopWords = ["", "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren't",
          "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can't",
          "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during",
          "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he",
          "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's",
          "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself", "let's",
          "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or",
          "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "shan't", "she", "she'd",
          "she'll", "she's", "should", "shouldn't", "so", "some", "such", "than", "that", "that's", "the", "their",
          "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're",
          "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't", "we",
          "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where", "where's",
          "which", "while", "who", "who's", "whom", "why", "why's", "with", "won't", "would", "wouldn't", "you",
          "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves"]

def standardize(char):
    char = re.sub(r"http://[a-zA-Z0-9-\./]* ", " ", char)
    char = re.sub(r"http://[a-zA-Z0-9-\./]*$", " ", char)
    char = re.sub('^(RT @.+?: )+', ' ', char)
    char = re.sub('#\w+', ' ', char)
    char = re.sub(r'[^\w]', ' ', char)
    char = char.lower()
    char = char.strip()
    return char

def readJsonFile(filename):
    file = open(filename,"r")
    data = {}
    for row in file.readlines():
        instance = json.loads(row)
        key = str(instance['id'])
        value = standardize(instance['text'])
        data[key] = value
    return data

def wordsdata(str):
    words = str.strip().split(' ')
    set = []
    for word in words:
        if (word != ' ' and word != 'rt'):
            if word not in set and word not in stopWords:
                set.append(word)
    return set

def jaccardDistance(str1, str2):
    words1 = wordsdata(str1)
    words2 = wordsdata(str2)
   
    intersection = 0
    for word1 in words1:
        if word1 != ' ':
            if word1 in words2:
                intersection += 1
    union = len(words1) + len(words2) - intersection
    if union == 0:
        return 1
    return 1 - float(intersection)/float(union)

def Index(filename, clusters):
    file = open(filename)
    line = ''
    for row in file.readlines():
        row = row.strip()
        if row != ' ':
            line = line + row
    indices = line.split(',')
    u = 0
    if(clusters < 25):
        indices1 = indices[:clusters]
        return indices1
    return indices

def computeSSE(labels, data):
    sum = 0
    for item in data:
        id = str(item)
        str1 = data[id]
        str2 = data[labels[id]]
        dist = jaccardDistance(str1, str2)
        sum += dist ** 2
    return sum

def main(args):
    clusters = args[1]#25
    seedsFile = args[2]#"K:\\Dhwani\\1stSem\\ML\\Ass6\\InitialSeeds.txt"
    tweetsFile = args[3]#"K:\Dhwani\Tweets.json"
    outputFile = args[4]#"K:\\Dhwani\\1stSem\\ML\\Ass6\\tweets-k-means-output.txt"
    data = readJsonFile(tweetsFile)
    indices = Index(seedsFile, clusters)
    list = {}
    num = 1
    label = {}
    trial = []
    if(clusters>25):
        # print range(25,clusters)
        for ui in range(25,clusters):
            # print ui
            for items in data:
                id1 = str(items)
                if(id1 not in indices):
                    indices.append(id1)
                    break
                else:
                    trial.append(id1)

    trial1 = []
    count = 0
    for index in indices:
        list[index] = str(num) + "   " + index
        count+=1
        # print(str(num) + "   " + index)
        num += 1
    for item in data:
        id = str(item)
        trial1.append(id)
        # print id
        str1 = data[id]
        # print str1
        dist = 2
        # selectedIndex = ''
        # print len(indices)
        for index in indices:
            if index in trial1:
                trial1.remove(index)

        for i in indices:
            str2 = data[i]
            # print str2
            if jaccardDistance(str1, str2) < dist:
                dist = jaccardDistance(str1, str2)
                selectedIndex = i
        label[id] = selectedIndex

        # original = list[selectedIndex]
        if id in trial1 and id not in list[selectedIndex]:
            list[selectedIndex] += ', ' + id
            count+=1
        # print label[id]
    # print len(label)
    # list[selectedIndex] += ',,,, '
    # print trial1
    file = open(outputFile, "w")
    # len(indices)
    # if clusters<=25 else 25
    # print list
    for i7 in range(0, clusters):
        # print i7
        # print list[indices[i7]]
        # print list
        file.write(list[indices[i7]])
        file.write('\n')
        file.write('\n')
    sse = computeSSE(label, data)
    file.write("Total Id Count: "+str(count))
    file.write('\n')
    file.write('\n')
    print("Output File Generated : "+outputFile)
    sse = "\nSSE : " + str(sse)
    file.write(sse)
    # print (sse)
    file.close()

main(sys)