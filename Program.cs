using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;
using Pedidos.Api.Data;
using Pedidos.Api.Services;

var builder = WebApplication.CreateBuilder(args);

var connectionString = builder.Configuration.GetConnectionString("Farmacia")
    ?? throw new InvalidOperationException("A connection string 'Farmacia' não foi configurada.");

builder.Services.AddDbContext<FarmaciaDbContext>(options =>
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString)));
builder.Services.AddScoped<PedidoService>();
builder.Services.AddControllers().AddJsonOptions(options =>
    options.JsonSerializerOptions.Converters.Add(new JsonStringEnumConverter()));
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddCors(options => options.AddPolicy("FrontEnd", policy => policy
    .AllowAnyOrigin().AllowAnyHeader().AllowAnyMethod()));

var app = builder.Build();
app.UseSwagger();
app.UseSwaggerUI();
app.UseCors("FrontEnd");
app.MapControllers();
app.MapGet("/", () => Results.Ok(new { sistema = "FarmaSync - API de pedidos", status = "online" }));
app.Run();
