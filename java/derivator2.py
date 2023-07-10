from jinja2 import Environment, FileSystemLoader
import itertools

template_values = {
    'hasWithdrawalLimit': [True, False],
    'supportsCurrency': [True, False],
    'isSingleAccount': [True, False],
    'hasConsortium': [True, False],
}

keys = list(template_values.keys())

combinations = list(itertools.product(*template_values.values()))
combinations = [dict(zip(keys, combination)) for combination in combinations]

print(combinations)

# Configure Jinja and ready the template
env = Environment(loader=FileSystemLoader('.'))

i = 0
for combination in combinations:
    template = env.get_template('variant2.j2')  # This line is moved inside the loop
    output = template.render(**combination)
    with open(f'VariantImpr{str(i)}.java', 'w') as f:
        f.write(output)
    i = i + 1

# Configure Jinja and ready the template
env = Environment(loader=FileSystemLoader('.'))

variants = {
    'VariantRevisit1': {
        'hasWithdrawalLimit': True,
        'supportsCurrency': False,
        'isSingleAccount': False,
        'hasConsortium': True
    },
    'VariantRevisit2': {
        'hasWithdrawalLimit': True,
        'supportsCurrency': True,
        'isSingleAccount': True,
        'hasConsortium': False
    },
    'VariantRevisit3': {
        'hasWithdrawalLimit': False,
        'supportsCurrency': True,
        'isSingleAccount': False,
        'hasConsortium': True
    },
}

for variant_name, variant_values in variants.items():
    template = env.get_template('variant2.j2')
    output = template.render(**variant_values)  # Unpack the dictionary
    with open(f'{variant_name}.java', 'w') as f:
        f.write(output)
