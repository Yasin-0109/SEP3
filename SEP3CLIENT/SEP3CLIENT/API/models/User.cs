using System;
using System.Linq;

namespace FreshFitness.API.models
{
    class User
    {
        public int id { get; set; }
        public string firstName { get; set; }
        public string lastName { get; set; }
        public string dateOfBirth { get; set; }
        public string email { get; set; }
        public string password { get; set; }
        public int phoneNumber { get; set; }
        public UserRole userRole { get; set; }

        public string getName {
            get
            {
                return firstName + " " + lastName;
            }
        }

        public DateTime dateOfBirthDT
        {
            get
            {
                return DateTime.Parse(this.dateOfBirth);
            }
        }

        public int getSubscriptionId
        {
            get
            {
                if (Global.getSubscriptions() != null)
                {
                    Subscription sub = Global.getSubscriptions().FirstOrDefault(a => a.userId == this.id);
                    if (sub != null)
                    {
                        return sub.id;
                    }
                }
                return -1;
            }
        }

        public string getSubscriptionTypeName
        {
            get
            {
                if (Global.getSubscriptions() != null)
                {
                    Subscription sub = Global.getSubscriptions().FirstOrDefault(a => a.userId == this.id);
                    if (sub != null)
                    {
                        return sub.subscriptionType.type;
                    }
                }
                return "Unknown";
            }
        }

        public int getSubscriptionTypeId
        {
            get
            {
                if (Global.getSubscriptions() != null)
                {
                    Subscription sub = Global.getSubscriptions().FirstOrDefault(a => a.userId == this.id);
                    if (sub != null)
                    {
                        return sub.subscriptionType.id;
                    }
                }
                return -1;
            }
        }
    }
}
