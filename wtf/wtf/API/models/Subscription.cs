using System;

namespace FreshFitness.API.models
{
    class Subscription
    {
        public int id { get; set; }
        public int userId { get; set; }
        public string validFrom { get; set; }
        public string validTo { get; set; }
        public SubscriptionType subscriptionType { get; set; }

        public DateTime validFromDT
        {
            get
            {
                return DateTime.Parse(this.validFrom);
            }
        }
        
        public DateTime validToDT
        {
            get
            {
                return DateTime.Parse(this.validTo);
            }
        }
    }
}
