using System;

namespace FreshFitness.API.models
{
    class Workout
    {
        public int id { get; set; }
        public int userId { get; set; }
        public WorkoutType type { get; set; }
        public int numberOfWorkouts { get; set; }
        public string date { get; set; }

        public string formattedWDate
        {
            get {
                DateTime date = DateTime.Parse(this.date);
                return date.ToString("dddd d");
            }
        }

        public DateTime dateTime
        {
            get
            {
                return DateTime.Parse(this.date);
            }
        }
    }
}
