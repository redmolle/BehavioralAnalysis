using BehavioralAnalysisLog.Db;
using BehavioralAnalysisLog.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BehavioralAnalysisLog.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LogController : ControllerBase
    {
        public LogController(LogContext logContext)
        {
            this.logContext = logContext;
        }

        LogContext logContext = null;

        [HttpGet("raw")]
        public IActionResult Raw(Guid? id)
        {
            var result = logContext.Raw.AsQueryable();

            if (id != null)
            {
                result.FirstOrDefault(x => x.Id == id);
            }

            return Ok(result.ToList());
        }

        [HttpPost("raw")]
        public IActionResult SaveRaw([FromBody]object request)
        {
            var raw = new Raw
            {
                Value = request.ToString()
            };

            logContext.Raw.Add(raw);
            logContext.SaveChanges();

            return CreatedAtAction("Raw", raw.Id);
        }
    }
}
