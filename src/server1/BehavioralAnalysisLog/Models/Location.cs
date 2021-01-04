using Newtonsoft.Json;

namespace server.Models
{
    public class Location : BaseEntity
    {
        [JsonProperty("enabled")]
        public bool Enabled { get; set; }

        [JsonProperty("latitude")]
        public double Latitude { get; set; }

        [JsonProperty("longitude")]
        public double Longitude { get; set; }

        [JsonProperty("accuracy")]
        public double Accuracy { get; set; }

        [JsonProperty("altitude")]
        public double Altitude { get; set; }

        [JsonProperty("speed")]
        public double Speed { get; set; }
    }
}
