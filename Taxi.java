import java.util.*;
class CallTaxiException extends Exception
{
  String msg;

  CallTaxiException()
  {
     msg=null;
  }

  CallTaxiException(String msg)
  {
    this.msg = msg;
  }

  public String toString()
  {
     return "CallTaxiException -  "+msg;
  }
}  

class Taxi
{
   int taxi_no;
   int bkid;
   char pickpt;
   char droppt;
   int picktime;
   int droptime;
   double fare;
   double amt;

 Taxi()
{
   taxi_no = 0;
   bkid = 0;
   pickpt = 'A';
   droppt = 'A';
   picktime = 0;
   droptime = 0;
   fare = 0;
   amt=0;
}

 Taxi(int taxi_no, int bkid, char pickpt, char droppt, int picktime, int droptime, double fare, double amt)
 {
   this.taxi_no = taxi_no;
   this.bkid = bkid;
   this.pickpt = pickpt;
   this.droppt = droppt;
   this.picktime = picktime;
   this.droptime = droptime;
   this.fare = fare;
   this.amt = amt;
 }

  Taxi(Taxi p)
{
this.taxi_no = p.taxi_no;
this.bkid = p.bkid;
this.pickpt = p.pickpt;
this.droppt = p.droppt;
this.picktime = p.picktime;
this.droptime = p.droptime;
this.fare = p.fare;
this.amt = p.amt;
}


  public String toString()
  {
    return taxi_no+"\t"+bkid+"\t"+pickpt+"\t"+droppt+"\t"+picktime+"\t"+droptime+"\t"+fare+"\t"+amt;
  }
}

class CallTaxi
{
  int n;
  Taxi t[];
  static int taxiIncr=1211;
  static int bkid=1300;
  List<Taxi> avail;

  CallTaxi()
  {
    n = 0;
    t = null;
    avail = null;
  }
  
  CallTaxi(int n)
  {
    this.n=n;
    t = new Taxi[n];
    for(int i=0;i<n;i++)
    t[i] = new Taxi(taxiIncr++, bkid++, 'A', 'A', 0, 0, 0, 0);
  }

  Boolean check(char k)
  {
     return k>='A' && k<='F';
  }

  Boolean check(int m)
  {
     return m>=1 && m<=24;
  }
  
   Taxi checktaxi(char pickpt, char droppt, int picktime)
   {
    avail  = new ArrayList<>();
    for(int i=0;i<6;i++)
    {
    for(Taxi k:t)
    if(picktime >= k.droptime)
    {
      if(pickpt-i == k.droppt)
      avail.add(k);
     
      if(pickpt+i == k.droppt && i!=0)
      avail.add(k);
   }
   if(avail.size()>0)
   break;
   }
   return avail.stream().min((x,y)->Double.compare(x.amt, y.amt)).get();
   }
      
   

  void booking()throws CallTaxiException
  {
    Scanner sc = new Scanner(System.in);
   
    System.out.println("Enter pickup point: ");
    char pickpt = sc.next().toUpperCase().charAt(0);
    
    if(!check(pickpt))
    throw new CallTaxiException("No service available for this pickup point: "+pickpt);

    System.out.println("Enter drop point: ");
    char droppt = sc.next().toUpperCase().charAt(0);
 
    if(!check(droppt)|| droppt==pickpt)
    throw new CallTaxiException("No service available for this drop point: "+droppt);

    System.out.println("Enter pickup time: ");
    int picktime = sc.nextInt();
    
    if(!check(picktime))
    throw new CallTaxiException("No service available! Sorry "+picktime);
  
  

  Taxi at = checktaxi(pickpt, droppt, picktime);

  if(at == null)
  throw new CallTaxiException("No taxi available now");
   at.pickpt = pickpt;
   at.droppt = droppt;
   at.picktime = picktime;
   at.droptime = Math.abs(droppt - pickpt)+picktime;
   if(at.droptime > 24)
   at.droptime = at.droptime - 24;
   at.fare = 100 + (Math.abs(droppt - pickpt) * 15 -5) * 10;
   at.amt = at.amt + at.fare;
   for(int i=0;i<n;i++)
  if(t[i].taxi_no==at.taxi_no)
 {
    t[i]= new Taxi(at);
    disp(t[i]);
}
}
void disp(Taxi k)
{
System.out.println(k);
}
 void display()
 {
   for(Taxi l: t)
   System.out.println(l);
}

}

class Driver
{
   public static void main(String args[])
  {
   Scanner sc = new Scanner(System.in);
int ch;
System.out.println("Enter no of Taxies  : ");
int n = sc.nextInt();
CallTaxi  ct = new CallTaxi(n);
do
{
System.out.println("Simren Taxi");
System.out.println("Booking ------ 1");
System.out.println("Display ------ 2");
System.out.println("Exit --------- 3");
System.out.println("Enter your choice: ");
ch = sc.nextInt();
switch(ch)
{
case 1 :
try
{
ct.booking();
}
catch(CallTaxiException e)
{
  System.out.println(e);
}
break;
case 2:
ct.display();
break;
case 3:
break;
default :
System.out.println("Invalid choice");
}
}while(ch != 3);
}
}


   



