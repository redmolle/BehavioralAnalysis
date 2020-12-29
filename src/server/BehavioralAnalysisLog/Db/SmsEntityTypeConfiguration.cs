using BehavioralAnalysisLog.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace BehavioralAnalysisLog.Db
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
