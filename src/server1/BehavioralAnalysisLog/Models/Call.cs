using Newtonsoft.Json;

namespace server.Models
{
    public class Call : BaseEntity
    {
        [JsonProperty("number")]
        public string Number { get; set; }

        [JsonProperty("name")]
        public string Name { get; set; }

        [JsonProperty("duration")]
        public string Duration { get; set; }

        [JsonProperty("date")]
        public string Date { get; set; }

        [JsonProperty("type")]
        public int Type { get; set; }
    }
}
