using Newtonsoft.Json;

namespace server.Models
{
    public class RawLog : BaseEntity
    {
        [JsonProperty("value")]
        public string Value { get; set; }
    }
}
