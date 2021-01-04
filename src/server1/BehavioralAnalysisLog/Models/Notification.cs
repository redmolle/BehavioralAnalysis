using Newtonsoft.Json;

namespace server.Models
{
    public class Notification : BaseEntity
    {
        [JsonProperty("appName")]
        public string AppName { get; set; }

        [JsonProperty("title")]
        public string Title { get; set; }

        [JsonProperty("content")]
        public string content { get; set; }

        [JsonProperty("postTime")]
        public long PostTime { get; set; }

        [JsonProperty("key")]
        public string Key { get; set; }
    }
}
