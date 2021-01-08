using bas.Models;
using bas.Services.Log;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace bas.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoggerController : ControllerBase
    {
        public LoggerController(ILogService logService)
        {
            _logService = logService;
        }

        private readonly ILogService _logService;

        [HttpPost]
        public IActionResult Log([FromBody] LogRequest request)
        {
            return CreatedAtAction("get", _logService.CreateLog(request));
        }

        [HttpGet]
        [Route("all")]
        //[Authorize(Roles = UserRole.Admin)]
        public IActionResult Get()
        {
            return Ok(_logService.GetAllLogs());
        }
    }
}
