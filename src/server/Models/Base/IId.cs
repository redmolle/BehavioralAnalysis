using System;

namespace server.Models.Base
{
    public interface IId
    {
        Guid Id { get; set; }
    }
}
