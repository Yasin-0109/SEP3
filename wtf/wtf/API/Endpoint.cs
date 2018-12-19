using System;

namespace Client.API
{
    class Endpoint
    {
        private readonly string route;
        //private static readonly Uri serverUrl = new Uri("https://localhost:8080"); // LOCAL Server
        private static readonly Uri serverUrl = new Uri("https://api3.mplauncher.pl:8888"); // GLOBAL Server

        // Endpoints
        public static readonly Endpoint STATUS = new Endpoint("/status");
        public static readonly Endpoint LOGIN = new Endpoint("/login");
        public static readonly Endpoint LOGOUT = new Endpoint("/logout");

        // Admin
        public static readonly Endpoint AdminActivitiesList = new Endpoint("/admin/activities/");
        public static readonly Endpoint AdminActivitiesAdd = new Endpoint("/admin/activities/add");
        public static readonly Endpoint AdminActivitiesEdit = new Endpoint("/admin/activities/{0:d}/edit");
        public static readonly Endpoint AdminActivitiesDelete = new Endpoint("/admin/activities/{0:d}/delete");
        public static readonly Endpoint AdminSubscriptionsList = new Endpoint("/admin/subscriptions/");
        public static readonly Endpoint AdminSubscriptionsAdd = new Endpoint("/admin/subscriptions/add");
        public static readonly Endpoint AdminSubscriptionsEdit = new Endpoint("/admin/subscriptions/{0:d}/edit");
        public static readonly Endpoint AdminSubscriptionTypesList = new Endpoint("/admin/subscriptions/types/");
        public static readonly Endpoint AdminUsersList = new Endpoint("/admin/users/");
        public static readonly Endpoint AdminUsersGetByEmail = new Endpoint("/admin/users/getbyemail");
        public static readonly Endpoint AdminUsersAdd = new Endpoint("/admin/users/add");
        public static readonly Endpoint AdminUsersEdit = new Endpoint("/admin/users/{0:d}/edit");
        public static readonly Endpoint AdminUsersDelete = new Endpoint("/admin/users/{0:d}/delete");
        public static readonly Endpoint AdminRolesList = new Endpoint("/admin/roles/");

        // Instructor
        public static readonly Endpoint InstructorActivitiesList = new Endpoint("/instructor/activities/");
        public static readonly Endpoint InstructorActivitiesAdd = new Endpoint("/instructor/activities/add");
        public static readonly Endpoint InstructorActivitiesEdit = new Endpoint("/instructor/activities/{0:d}/edit");
        public static readonly Endpoint InstructorActivitiesDelete = new Endpoint("/instructor/activities/{0:d}/delete");

        // User
        public static readonly Endpoint UserInfo = new Endpoint("/user/");
        public static readonly Endpoint UserActivities = new Endpoint("/user/activities/");
        public static readonly Endpoint UserSubscriptionInfo = new Endpoint("/user/subscription/");
        public static readonly Endpoint UserUserActivities = new Endpoint("/user/useractivities/");
        public static readonly Endpoint UserUserActivityAdd = new Endpoint("/user/useractivities/add");
        public static readonly Endpoint UserUserActivityDelete = new Endpoint("/user/useractivities/{0:d}/delete");
        public static readonly Endpoint UserWorkouts = new Endpoint("/user/workouts/");
        public static readonly Endpoint UserWorkoutsTypes = new Endpoint("/user/workouts/types");
        public static readonly Endpoint UserWorkoutAdd = new Endpoint("/user/workouts/add");
        public static readonly Endpoint UserWorkoutEdit = new Endpoint("/user/workouts/{0:d}/edit");

        private Endpoint(string route)
        {
            this.route = route;
        }

        public override string ToString()
        {
            return route;
        }

        public string ToUrl()
        {
            return new Uri(serverUrl, route).AbsoluteUri;
        }

        public static Uri getServerUrl()
        {
            return serverUrl;
        }
    }
}
