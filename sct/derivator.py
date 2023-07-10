from jinja2 import Environment, FileSystemLoader

# Define the variants
variants = [1, 2, 3]
# concatenate "Variant" with each variant number
variants = [f'Variant{variant}' for variant in variants]

# Configure Jinja and ready the template
env = Environment(loader=FileSystemLoader('.'))
template = env.get_template('scm.j2')

# For each variant, produce the Java source code
for variant in variants:
    print(variant)
    output = template.render(variant=variant)
    with open(f'VariantSCM{variant}.dot', 'w') as f:
        f.write(output)