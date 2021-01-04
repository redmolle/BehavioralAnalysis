using server.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Innofactor.EfCoreJsonValueConverter;

namespace server.Db
{
    public class RawEntityTypeConfiguration : IEntityTypeConfiguration<Log>
    {
        public void Configure(EntityTypeBuilder<Log> builder)
        {
            builder.ToTable("Log");
            builder.HasKey(x => x.Id);
            builder.Property(x => x.Value).HasJsonValueConversion();
        }
    }
}
