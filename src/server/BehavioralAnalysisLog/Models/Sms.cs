using Newtonsoft.Json;

namespace BehavioralAnalysisLog.Models
{
    public class Sms : BaseEntity
    {
        [JsonProperty("address")]
        public string Address { get; set; }

        [JsonProperty("type")]
        public string Type { get; set; }

        [JsonProperty("body")]
        public string Body { get; set; }

        [JsonProperty("read")]
        public string Read { get; set; }
    }
}
