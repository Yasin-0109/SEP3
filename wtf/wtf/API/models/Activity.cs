using System;
using System.Linq;

namespace FreshFitness.API.models
{
    class Activity
    {
        public int id { get; set; }
        public string name { get; set; }
        public string startTime { get; set; }
        public string endTime { get; set; }
        public int instructorId { get; set; }
        public string instructorName { get; set; }

        public bool isUserIn
        {
            get
            {
                if (Global.getUserActivitiesIds() != null)
                {
                    return Global.getUserActivitiesIds().Contains(id);
                }
                return false;
            }
        }

        public string isUserInString
        {
            get
            {
                if (Global.getUserActivitiesIds() != null)
                {
                    return Global.getUserActivitiesIds().Contains(id) ? "Yes" : "No";
                }
                return "?";
            }
        }

        public string sTDateTimeH
        {
            get
            {
                DateTime date = DateTime.Parse(this.startTime);
                return date.ToString("hh':'mm");
            }
        }

        public DateTime sTDateTime
        {
            get
            {
                return DateTime.Parse(this.startTime);
            }
        }

        public DateTime eTDateTime
        {
            get
            {
                return DateTime.Parse(this.endTime);
            }
        }

        public TimeSpan howLong
        {
            get
            {
                return eTDateTime.Subtract(sTDateTime);
            }
        }

        public string howLongH
        {
            get
            {
                return howLong.Hours + ":" + howLong.Minutes;
            }
        }
    }
}
