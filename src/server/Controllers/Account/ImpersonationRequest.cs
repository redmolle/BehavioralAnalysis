using Newtonsoft.Json;

namespace server.Controllers.Account
{
    public class ImpersonationRequest
    {
        [JsonProperty("username")]
        public string UserName { get; set; }
    }
}
