import java.util.GregorianCalendar; 
import java.util.Date;

public class MyDate
{
private int day;
private int month;
private int year;

public MyDate(int d,int m,int y) //3 argument constructor
{
   day=d;
   month=m;
   year=y;
}

//this is the no-argument constructor public MyDateconstructors()
//the set value is 1 if nothing is typed in 
 public MyDate()

{
   day = 16;
   month = 4;
   year = 1997;
}
public void setDay(int d)
{
         day = d;        
}
public void setMonth(int m)
{
   month = m;
}
public void setYear(int y)
{
   year = y;
}
public int getDay()
{
   return day;
}
public int getMonth()
{
   return month;
}
public int getYear()
{
   return year;
} 
public boolean isLeapYear()
{
      if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)

      {
         return true;
      }
      else
      {
         return false;
      }
   }
// from exercise 7.03
public int daysInMonth()
{
   if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month ==9 || month == 12)
   {
      return 31;
   }
  
   else if (month == 4 || month == 6 || month == 10 || month == 11)
   {
      return 30;
   }
   
   else if (month == 2 && isLeapYear())   
   {
      return 29;
   }
   
   else if ( month == 2 && !isLeapYear()) 
   {
      return 28;
   }
   else
   {
      return -1;
   }
}

public void nextDay()
{
   
   if (day +1 > daysInMonth())
   {
      
   if (month +1 > 12)
   {
      
      year++;
      month=1;
      day = 1;
      
   }
   else
   {
      month++;
      day=1;
   }
   }
   else 
   {
      day++;

}}

//exercise 7.03
public String getAtroSign()
{
   if ((month == 3 && day > 20) || (month == 4 && day < 20))
   {
      return "Aries";
   }
   
   else if ((month == 4 && day > 19) || (month == 5 && day < 21))
   
   {
      return "Taurus";
   }
   
   else if ((month == 5 && day > 20) || (month == 6 && day < 21))
   
   {
      return "Gemini";
   }
   else if ((month == 6 && day > 20) || (month == 7 && day < 23))
      
   {
      return "Cancer";
      
   }
   else if ((month == 7 && day > 22) || (month == 8 && day > 23))
         
   {
      return "Leo";
   }
   else if ((month == 8 && day > 22 || (month == 9 && day > 23)))
      
   {
      return "Virgo";
   }
   else if ((month == 9 && day > 22) || (month == 10 && day > 23))
      
   {
      return "Libra";
   }
   else if ((month == 10 && day > 22) || (month == 11 && day > 22))
         
   {
      return "Scorpio";
   }
   else if ((month == 11 && day > 21) || (month == 12 && day > 22))
      
   {
      return "Sagittarius";     
   }
   else if ((month == 12 && day > 21) || (month == 1 && day > 20))
      
   {
      return "Capricon";
   }
   else if ((month == 1 && day > 19) || (month == 2 && day > 19))
      
   {
      return "Aquarius";
   }
   else if ((month == 2 && day > 18) || (month == 3 && day > 19));
      
   {
      return "Pisces";
}
}
public String dayOfWeek()
{
   int q=day;
   int y=year;
   int m=month;
   if(month==1 || month==2)
   {
      m=m+12;
      y=y-1;
   }
    
   int k=year%100;
   int j=year/100;
   int h;
   h=( q+ ( 13 * ( m+  1 ) ) / 5+ k  + ( k / 4 ) + ( j / 4) + ( 5 * j )) % 7;
   if (h==0)
   {
      return "Saturday";
   }
   else if (h==1)
   {
      return "Sunday";
   }
   else if (h==2)
   {
      return "Monday";
   }
   else if (h==3)
   {
      return "Tuesday";
   }
   else if (h==4)
   {
      return "Wednesday";
   }
   else if (h==5)
   {
      return "Thursday";
   }
   else if (h==6)
   {
      return "Friday";
   }
   
   else
   {
      return "na";
   }
}

   public String getMonthName()
   {
      switch (month)
      {
         case 1:
            return "January";
         case 2:
            return "February";
         case 3:
            return "March";
         case 4:
            return "April";
         case 5:
            return "May";
         case 6:
            return "June";
         case 7:
            return "July";
         case 8:
            return "August";
         case 9:
            return "September";
         case 10:
            return "October";
         case 11:
            return "November";
         case 12:
            return "December";
            
         default:
            return "-1";
      }
   }   

public boolean equals(MyDate obj)
{
      if (day == obj.day && 
            month == obj.month && 
            year == obj.year)
      {
        return true;
      }
      else
      {
         return false;
   }
}

public MyDate copy()
{
   return new MyDate(day, month, year);

}

public MyDate(MyDate obj)
{
   day=obj.day;
   month=obj.month;
   year=obj.year;
}

   public void nextDays(int days)
   {

      for (int i = 0; i < days; i++)
      {
         nextDay();
      }
   }

public static MyDate today()
{
   GregorianCalendar currentDate = new GregorianCalendar();
   int currentDay = currentDate.get(GregorianCalendar.DATE);
   int currentMonth = currentDate.get(GregorianCalendar.MONTH)+1;
   int currentYear = currentDate.get(GregorianCalendar.YEAR);

   return new MyDate(currentDay, currentMonth, currentYear);

}

public boolean isBefore(MyDate date2)
{
   if ((day>date2.day && month==date2.month && year==date2.year)||
         (day>date2.day && month>date2.month && year==date2.year)  
         ||(day==date2.day && month>date2.month && year>date2.year)||
         ((day>date2.day && month==date2.month && year>date2.year)||
               (day==date2.day && month==date2.month && year>date2.year)||
         (day==date2.day && month>date2.month && year==date2.year)||
         (day<date2.day && month>date2.month && year>date2.year)||
         (day<date2.day && month<date2.month && year>date2.year))||
         (day>date2.day && month<date2.month && year>date2.year))
      
   {
      return true;
   }
   
   else 
   {
      return false;
   }
}

public String toString()
{
   return day + "/" + month + "/" + year;
}



public int displayDate()
{
   return day/month/year;
}
}

