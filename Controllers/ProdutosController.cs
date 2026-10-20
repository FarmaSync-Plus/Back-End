using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Pedidos.Api.Data;
using Pedidos.Api.DTOs;

namespace Pedidos.Api.Controllers;

[ApiController]
[Route("api/produtos")]
public sealed class ProdutosController(FarmaciaDbContext db) : ControllerBase
{
    [HttpGet]
    public async Task<ActionResult<IEnumerable<ProdutoResponse>>> Listar(
        [FromQuery] string? busca, [FromQuery] int? categoriaId, CancellationToken cancellationToken)
    {
        var query = db.Produtos.AsNoTracking().AsQueryable();
        if (!string.IsNullOrWhiteSpace(busca))
            query = query.Where(p => p.Nome.Contains(busca) || (p.Descricao != null && p.Descricao.Contains(busca)));
        if (categoriaId.HasValue)
            query = query.Where(p => p.CategoriaId == categoriaId.Value);

        var produtos = await query.OrderBy(p => p.Nome).ToListAsync(cancellationToken);
        return Ok(produtos.Select(p => new ProdutoResponse(p.IdProduto, p.Nome.Trim(), p.Descricao, p.Preco,
            p.Estoque, p.Fabricante, p.DataValidade, p.PrecisaReceita, p.CategoriaId, p.Imagem)));
    }

    [HttpGet("{id:int}")]
    public async Task<ActionResult<ProdutoResponse>> Obter(int id, CancellationToken cancellationToken)
    {
        var p = await db.Produtos.AsNoTracking().FirstOrDefaultAsync(x => x.IdProduto == id, cancellationToken);
        if (p is null) return NotFound(new { mensagem = "Produto não encontrado." });
        return Ok(new ProdutoResponse(p.IdProduto, p.Nome.Trim(), p.Descricao, p.Preco, p.Estoque,
            p.Fabricante, p.DataValidade, p.PrecisaReceita, p.CategoriaId, p.Imagem));
    }
}
