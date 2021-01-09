using Newtonsoft.Json;
using System.Collections.Generic;

namespace site.Models
{
    public class LogPageResult
    {
        [JsonProperty("logs")]
        public IEnumerable<LogResult> Logs { get; set; } = new LogResult[] { };

        [JsonProperty("maxPage")]
        public int MaxPage { get; set; } = 0;
    }
}
