using Microsoft.EntityFrameworkCore.Migrations;

namespace server.Migrations
{
    public partial class Removecolumn : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Type",
                table: "Raw");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "Type",
                table: "Raw",
                type: "integer",
                nullable: false,
                defaultValue: 0);
        }
    }
}
