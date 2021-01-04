using System;

namespace server.Models.Base
{
    public class IdModel : IId
    {
        public Guid Id { get; set; }
    }
}
