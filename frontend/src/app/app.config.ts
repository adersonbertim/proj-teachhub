import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors, withFetch } from '@angular/common/http';
import { MatPaginatorIntl } from '@angular/material/paginator';

import { routes } from './app.routes';
import { authInterceptor } from './core/interceptors/auth.interceptor';

function paginadorEmPortugues(): MatPaginatorIntl {
  const paginador = new MatPaginatorIntl();
  paginador.itemsPerPageLabel = 'Itens por página:';
  paginador.nextPageLabel = 'Próxima página';
  paginador.previousPageLabel = 'Página anterior';
  paginador.firstPageLabel = 'Primeira página';
  paginador.lastPageLabel = 'Última página';
  paginador.getRangeLabel = (pagina: number, tamanho: number, total: number) => {
    if (total === 0) return `0 de 0`;
    const inicio = pagina * tamanho;
    const fim = Math.min(inicio + tamanho, total);
    return `${inicio + 1} – ${fim} de ${total}`;
  };
  return paginador;
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(
      withFetch(),
      withInterceptors([authInterceptor])
    ),
    { provide: MatPaginatorIntl, useFactory: paginadorEmPortugues }
  ],
};
