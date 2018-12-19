using Client;
using Client.API;
using Newtonsoft.Json;
using RestSharp;
using System.Collections.Generic;
using System.Windows.Forms;
using FreshFitness.API.models;

namespace FreshFitness.API
{
    class Global
    {
        // User activities

        private static List<int> userActivitiesIds;

        public static void refreshUserActivities()
        {
            var uAreq = new RestRequest(Endpoint.UserUserActivities.ToString(), Method.GET);
            var uAresponse = Program.getApiClient().getClient().Execute<result>(uAreq);
            if (uAresponse.IsSuccessful && uAresponse.Data.status)
            {
                userActivitiesIds = JsonConvert.DeserializeObject<List<int>>(uAresponse.Data.data);
            }
            else
            {
                // TODO: Error while getting user activities
            }
        }

        public static void addUserActivity(int id)
        {
            var aUAreq = new RestRequest(Endpoint.UserUserActivityAdd.ToString(), Method.POST);
            aUAreq.AddQueryParameter("activityId", id.ToString());
            Program.getApiClient().getClient().ExecuteAsync<result>(aUAreq, response =>
            {
                if (!response.IsSuccessful || !response.Data.status)
                {
                    MessageBox.Show(response.Data.message);
                }

                refreshUserActivities();
            });
        }

        public static void delUserActivity(int id)
        {
            var dUAreq = new RestRequest(string.Format(Endpoint.UserUserActivityDelete.ToString(), id), Method.DELETE);
            Program.getApiClient().getClient().ExecuteAsync<result>(dUAreq, response =>
            {
                if (!response.IsSuccessful || !response.Data.status)
                {
                    MessageBox.Show(response.Data.message);
                }

                refreshUserActivities();
            });
        }

        public static List<int> getUserActivitiesIds()
        {
            return userActivitiesIds;
        }

        // Admin - Subscription Types
        private static List<SubscriptionType> subscriptionTypes;

        public static void refreshSubscriptionTypes()
        {
            var req = new RestRequest(Endpoint.AdminSubscriptionTypesList.ToString(), Method.GET);
            var response = Program.getApiClient().getClient().Execute<result>(req);

            if (response.IsSuccessful && response.Data.status)
            {
                subscriptionTypes = JsonConvert.DeserializeObject<List<SubscriptionType>>(response.Data.data);
            }
            else
            {
                // TODO: Error while getting user subscription types
            }
        }

        public static List<SubscriptionType> getSubscriptionTypes()
        {
            return subscriptionTypes;
        }

        // Admin - Subscriptions
        private static List<Subscription> subscriptions;

        public static void refreshSubscriptions()
        {
            var req = new RestRequest(Endpoint.AdminSubscriptionsList.ToString(), Method.GET);
            var response = Program.getApiClient().getClient().Execute<result>(req);

            if (response.IsSuccessful && response.Data.status)
            {
                subscriptions = JsonConvert.DeserializeObject<List<Subscription>>(response.Data.data);
            }
            else
            {
                // TODO: Error while getting user subscriptions
            }
        }

        public static List<Subscription> getSubscriptions()
        {
            return subscriptions;
        }

        // Admin - User Roles
        private static List<UserRole> userRoles;

        public static void refreshUserRoles()
        {
            var req = new RestRequest(Endpoint.AdminRolesList.ToString(), Method.GET);
            var response = Program.getApiClient().getClient().Execute<result>(req);

            if (response.IsSuccessful && response.Data.status)
            {
                userRoles = JsonConvert.DeserializeObject<List<UserRole>>(response.Data.data);
            }
            else
            {
                // TODO: Error while getting user roles
            }
        }

        public static List<UserRole> getUserRoles()
        {
            return userRoles;
        }
    }
}
