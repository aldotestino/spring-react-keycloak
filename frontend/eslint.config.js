import antfu from '@antfu/eslint-config';

export default antfu({
  react: true,
  typescript: true,
  rules: {
    'no-console': 'warn',
    'node/no-process-env': 'warn',
    'ts/consistent-type-definitions': ['error', 'type'],
  },
  ignores: ['node_modules', 'dist', 'build', 'src/components/ui', 'src/routeTree.gen.ts'],
  stylistic: {
    indent: 2,
    quotes: 'single',
    semi: true,
  },
});