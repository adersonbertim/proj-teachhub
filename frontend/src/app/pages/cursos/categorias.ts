export interface CategoriaCurso {
  nome: string;
  icone: string;
}

export const CATEGORIAS_CURSO: CategoriaCurso[] = [
  { nome: 'Ferramentas digitais', icone: 'devices' },
  { nome: 'Planejamento de aulas', icone: 'event_note' },
  { nome: 'Metodologias ativas', icone: 'groups' },
  { nome: 'Avaliação', icone: 'fact_check' },
  { nome: 'Inclusão e acessibilidade', icone: 'accessibility_new' },
  { nome: 'IA na educação', icone: 'smart_toy' },
  { nome: 'Gestão de sala de aula', icone: 'co_present' },
  { nome: 'Formação continuada', icone: 'workspace_premium' }
];

export function iconeDaCategoria(nome?: string): string {
  return CATEGORIAS_CURSO.find(c => c.nome === nome)?.icone ?? 'school';
}

export function ehPlaylist(link?: string): boolean {
  return !!link && link.includes('list=');
}
