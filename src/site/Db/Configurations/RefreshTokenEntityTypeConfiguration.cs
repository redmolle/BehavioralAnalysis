using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using site.Db.Models;
using System;

namespace site.Db.Configurations
{
    public class RefreshTokenEntityTypeConfiguration : IEntityTypeConfiguration<RefreshToken>
    {
        public void Configure(EntityTypeBuilder<RefreshToken> builder)
        {
            builder
                .ToTable("RefreshToken");

            builder
                .HasKey(x => x.Id);

            builder
                .HasIndex(x => x.TokenString)
                .IsUnique();

            builder
                .Property<Guid>("UserId");

            builder
                .HasOne(x => x.User)
                .WithMany(x => x.RefreshTokens)
                .HasForeignKey("UserId");
        }
    }
}