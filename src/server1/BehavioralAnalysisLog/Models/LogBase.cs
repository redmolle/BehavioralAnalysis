using Newtonsoft.Json;

namespace server.Models
{
    public class LogBase : BaseEntity
    {
        [JsonProperty("type")]
        public LogType Type { get; set; }
    }
}
