using Newtonsoft.Json;

namespace BehavioralAnalysisLog.Models
{
    public class Contact : BaseEntity
    {
        [JsonProperty("name")]
        public string Name { get; set; }

        [JsonProperty("number")]
        public string Number { get; set; }
    }
}
