from jinja2 import Environment, FileSystemLoader

# Define the variants
variants = [1, 2, 3]

# Configure Jinja and ready the template
env = Environment(loader=FileSystemLoader('.'))
template = env.get_template('variant.j2')

# For each variant, produce the Java source code
for variant in variants:
    output = template.render(variant=variant)
    with open(f'Variant{variant}.java', 'w') as f:
        f.write(output)
