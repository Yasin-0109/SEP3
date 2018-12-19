
namespace FreshFitness.API.models
{
    class WorkoutType
    {
        public int id { get; set; }
        public string type { get; set; }

        public override string ToString()
        {
            return type;
        }
    }
}
