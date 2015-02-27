
package it.iovino.utilita;

/**
 *
 * @author antoiovi
 */
public class interPolar {
    
   public static double  interPolarLin(double x,double X[],double Y[]){
  int i=0;
double Xa;
double Xb;

 Xa=0;
 Xb=1;
 
for(i=0;i<X.length;i++){
    if(x==X[i])
        return Y[i];
    if(x>X[i])
        continue;
    else 
        break;
}
if(i==0)
    return Y[0];

if(i>=X.length)
    return X[i-1];
Xa=X[i-1];
Xb=X[i];
double Ya;    
double Yb;    
Ya=Y[i-1];
Yb=Y[i];
   //Calculate slope from p1 to p2 
double m = (Xb-Xa)/(Yb-Ya); 
double a=Ya*(x-Xb)/(Xa-Xb);
double b=Yb*(x-Xa)/(Xa-Xb);

return a-b;
    
}
}
