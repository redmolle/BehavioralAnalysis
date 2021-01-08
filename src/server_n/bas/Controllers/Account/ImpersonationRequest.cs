using Newtonsoft.Json;

namespace bas.Controllers.Account
{
    public class ImpersonationRequest
    {
        [JsonProperty("username")]
        public string UserName { get; set; }
    }
}
