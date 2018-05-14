
data = read.csv("K:\\Dhwani\\2ndSem\\Statistical Method\\mini Projects\\M6\\crime.csv")
str(data)
attach(data)


drawPlot = function(x,string){
  plot(x,murder.rate,xlab=string)
  abline(lm(murder.rate~x))
}
par(mfrow=c(3,2))
drawPlot(poverty,"poverty")
drawPlot(high.school,"high.school")
drawPlot(college,"college")
drawPlot(single.parent,"single.parent")
drawPlot(unemployed,"unemployed")
drawPlot(metropolitan,"metropolitan")

fit.1 = lm(murder.rate ~ poverty + high.school +college + unemployed + single.parent + metropolitan + region)
summary(fit.1)


par(mfrow=c(1,1))
#Model diagnostics for the model
#Residual plot
plot(fitted(fit.1),resid(fit.1))
abline(h=0)


fit.2 = update(fit.1, . ~ . - poverty - high.school - college - unemployed - metropolitan)
summary(fit.2)


fit.3=update(fit.2, . ~ . + poverty + high.school  )
summary(fit.3)


fit.4=update(fit.3, . ~ . - poverty + college +unemployed )
summary(fit.4)

fit.5=update(fit.4, .~. -college-unemployed +metropolitan )
summary(fit.5)

fit.without.high.school = update(fit.5, . ~ . - high.school)
summary(fit.without.high.school)


fit.without.region = update(fit.without.high.school, . ~ . - region)
summary(fit.without.region)

anova(fit.5, fit.without.high.school)

anova(fit.without.region,fit.without.high.school)


fit.forward = step ( lm( murder.rate ~ 1, data=data),   scope = list( upper = ~poverty + high.school + college + single.parent   + unemployed + metropolitan + region), direction = "forward")


fit.backward = step(lm(murder.rate ~ poverty + high.school + college + single.parent + unemployed + metropolitan + region, data = data),   scope = list(lower = ~1), direction = "backward")

fit.both = step(lm(murder.rate ~ 1, data=data),scope = list(upper = ~poverty + high.school + college + single.parent + unemployed + metropolitan + region, lower = ~1),direction = "both")
anova(fit.without.high.school, fit.both)

single.parent.mean = mean(single.parent)
metro.mean = mean(metropolitan)
summary(region)


region.val = "South"
predict(fit.without.high.school,data.frame("single.parent"= single.parent.mean,"metropolitan"=metro.mean,"region"=region.val))
