model.groovyValue=""

def r(a){def i=a.indexOf(48);if(i<0)model.groovyValue+=a else(('1'..'9')-(0..80).collect{j->g={(int)it(i)==(int)it(j)};g{it/9}|g{it%9}|g{it/27}&g{it%9/3}?a[j]:'0'}).each{r(a[0..<i]+it+a[i+1..-1])}}

r '200375169639218457571964382152496873348752916796831245900100500800007600400089001'