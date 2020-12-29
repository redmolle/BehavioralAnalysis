using BehavioralAnalysisLog.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace BehavioralAnalysisLog.Db
{
    public class CallEntityTypeConfiguration : IEntityTypeConfiguration<Call>
    {
        public void Configure(EntityTypeBuilder<Call> builder)
        {
            builder.ToTable("Call");
            builder.HasKey(x => x.Id);
        }
    }
}
