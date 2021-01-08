using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.SpaServices.ReactDevelopmentServer;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Newtonsoft.Json.Converters;
using bas.Db.Repository.User;
using bas.Db.Factory;
using bas.Db.Repository.RefreshToken;
using bas.Services.User;
using bas.Models;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using System;
using bas.Services.Jwt;
using bas.Db.Repository.Log;
using bas.Services.Log;

namespace bas
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services)
        {
            //var jwtTokenConfig = Configuration
            //    .GetSection(JwtTokenConfig.SECTION_NAME)
            //    .Get<JwtTokenConfig>();

            services
                .AddControllersWithViews()
                .AddNewtonsoftJson(options =>
                {
                    options.SerializerSettings.ReferenceLoopHandling = Newtonsoft.Json.ReferenceLoopHandling.Ignore;
                    options.SerializerSettings.Converters.Add(new StringEnumConverter());
                });

            //services.AddSingleton(jwtTokenConfig);
            //services
            //    .AddAuthentication(x =>
            //    {
            //        x.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
            //        x.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
            //    })
            //    .AddJwtBearer(x =>
                //{
                //    x.RequireHttpsMetadata = true;
                //    x.SaveToken = true;
                //    x.TokenValidationParameters = new TokenValidationParameters
                //    {
                //        ValidateIssuer = true,
                //        ValidIssuer = jwtTokenConfig.Issuer,
                //        ValidateIssuerSigningKey = true,
                //        IssuerSigningKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(jwtTokenConfig.Secret)),
                //        ValidAudience = jwtTokenConfig.Audience,
                //        ValidateAudience = true,
                //        ValidateLifetime = true,
                //        ClockSkew = TimeSpan.FromMinutes(1)
                //    };
                //});



            services.AddScoped<IContextFactory, ContextFactory>();

            services.AddScoped<IUserRepository>(provider => new UserRepository(provider.GetService<IContextFactory>()));
            services.AddScoped<IRefreshTokenRepository>(provider => new RefreshTokenRepository(provider.GetService<IContextFactory>()));
            services.AddScoped<ILogRepository>(provider => new LogRepository(provider.GetService<IContextFactory>()));

            //services.AddScoped<IUserService, UserService>();
            //services.AddSingleton<IJwtService, JwtService>();
            //services.AddHostedService<JwtRefreshTokenCacheService>();
            services.AddScoped<ILogService, LogService>();


            services.AddCors(options =>
            {
                options.AddPolicy("AllowAll", builder =>
                {
                    builder.AllowAnyOrigin()
                           .AllowAnyMethod()
                           .AllowAnyHeader();
                });
            });

            services
                .AddSpaStaticFiles(configuration =>
                {
                    configuration.RootPath = "ClientApp/build";
                });
        }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Error");
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();
            app.UseSpaStaticFiles();

            app.UseRouting();

            app.UseCors("AllowAll");

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllerRoute(
                    name: "default",
                    pattern: "{controller}/{action=Index}/{id?}");
            });

            app.UseSpa(spa =>
            {
                spa.Options.SourcePath = "ClientApp";

                if (env.IsDevelopment())
                {
                    spa.UseReactDevelopmentServer(npmScript: "start");
                }
            });
        }
    }
}
