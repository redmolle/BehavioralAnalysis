using server.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace server.Db
{
    public class WifiEntityTypeConfiguration : IEntityTypeConfiguration<Wifi>
    {
        public void Configure(EntityTypeBuilder<Wifi> builder)
        {
            builder.ToTable("Wifi");
            builder.HasKey(x => x.Id);
        }
    }
}
