using BehavioralAnalysisLog.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace BehavioralAnalysisLog.Db
{
    public class RawEntityTypeConfiguration : IEntityTypeConfiguration<Raw>
    {
        public void Configure(EntityTypeBuilder<Raw> builder)
        {
            builder.ToTable("Raw");
            builder.HasKey(x => x.Id);
        }
    }
}
