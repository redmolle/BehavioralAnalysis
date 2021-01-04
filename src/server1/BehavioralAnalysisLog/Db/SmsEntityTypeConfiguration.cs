using server.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace server.Db
{
    public class SmsEntityTypeConfiguration : IEntityTypeConfiguration<Sms>
    {
        public void Configure(EntityTypeBuilder<Sms> builder)
        {
            builder.ToTable("Sms");
            builder.HasKey(x => x.Id);
        }
    }
}
