using server.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace server.Db
{
    public class RawEntityTypeConfiguration : IEntityTypeConfiguration<RawLog>
    {
        public void Configure(EntityTypeBuilder<RawLog> builder)
        {
            builder.ToTable("Raw");
            builder.HasKey(x => x.Id);
        }
    }
}
