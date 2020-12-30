using Newtonsoft.Json;

namespace BehavioralAnalysisLog.Models
{
    public class RawLog : BaseEntity
    {
        [JsonProperty("value")]
        public string Value { get; set; }
    }
}
