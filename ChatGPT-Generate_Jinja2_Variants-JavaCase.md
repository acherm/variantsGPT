# Generate Jinja2 Variants

#### You:
I have the following template (in jinja2) :

package com.example;

import java.util.List;

public class Variant {

    class Account {
        private String id;
        private double amount;
        
        {% if hasWithdrawalLimit %}
        private double limit;
        {% endif %}
        
        {% if supportsCurrency %}
        private int currency;
        {% endif %}

        public void deposit(double amount) {}

        public double getAmount() {
            return 0.0;
        
        {% if hasWithdrawalLimit %}
        public void withdrawWithLimit(double amount) {}
        {% else %}
        public void withdrawWithoutLimit(double amount) {}
        {% endif %}

        {% if hasWithdrawalLimit %}
        public double getLimit() {
            return 0.0;
        }

        public void setLimit(double limit) {}
        {% endif %}

        {% if supportsCurrency %}
        public int getCurrency() {
            return 0;
        }

        public void setCurrency(int currency) {}
        {% endif %}
    }

    class Bank {
        {% if isSingleAccount %}
        private Account accounts;
        {% else %}
        private List<Account> accounts;
        {% endif %}
        
        {% if supportsCurrency %}
        private Converter converter;
        {% endif %}
        
        {% if hasConsortium %}
        private Consortium cons;
        {% endif %}

        public void depositOnAccount(String id, double amount) {}

        public void withdrawFromAccount(String id, double amount) {}

        {% if supportsCurrency %}
        public double convert(int curSource, int curTarget, double amount) {
            return 0.0;
        }
        {% endif %}
    }

    class Client {
        private String id;
        private String name;
    }

    {% if supportsCurrency %}
    class Converter {
        public double conv(int curSource, int curTarget, double amount) {
            return 0.0;
        }
    }
    {% endif %}

    {% if hasConsortium %}
    class Consortium {
        // Empty class as per the description.
    }
    {% endif %}
}

can you give me the Python code to derive all possible variants based on template variables?

#### ChatGPT:
To generate all possible combinations from this Jinja2 template, you can use Python's built-in `itertools.product` function. It can generate all combinations for a given list of values.

First, you need to import the necessary libraries:

```python
from jinja2 import Environment, BaseLoader
import itertools
```

Then, define the template:

```python
template = """
... # The entire Jinja2 template goes here ...
"""
```

Now you will set the values for each of the condition variables:

```python
template_values = {
    'hasWithdrawalLimit': [True, False],
    'supportsCurrency': [True, False],
    'isSingleAccount': [True, False],
    'hasConsortium': [True, False],
}
```

The next step is to create a list of the dictionary keys:

```python
keys = list(template_values.keys())
```

Now you'll use `itertools.product` to generate all possible combinations of these values:

```python
combinations = list(itertools.product(*template_values.values()))
```

Each combination is a tuple with the values in the same order as the keys. You can turn these combinations into dictionaries that can be used with the template:

```python
combinations = [dict(zip(keys, combination)) for combination in combinations]
```

Now you can use Jinja2 to generate all variants from the template:

```python
env = Environment(loader=BaseLoader)

for combination in combinations:
    template = env.from_string(template)
    rendered_template = template.render(combination)
    print(rendered_template)
```

This script will print out all the variants of the Jinja2 template with all possible combinations of the template variables.

Please replace `template = """ ... """` with your Jinja2 template.


#### You:
why this code produces the same 15 Java variants:

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
template = env.get_template('variant2.j2')

i = 0
for combination in combinations:
    output = template.render(variant=combination)
    with open(f'VariantImpr{str(i)}.java', 'w') as f:
        f.write(output)
    i = i + 1

#### ChatGPT:
This issue occurs because the Jinja2 template object is being mutated in each iteration of the loop. The `template.render` method doesn't create a new instance, it modifies the existing one. So, when you reuse the `template` object, it already has a state from the previous rendering.

To fix this, move the line `template = env.get_template('variant2.j2')` inside the loop:

```python
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
    output = template.render(variant=combination)
    with open(f'VariantImpr{str(i)}.java', 'w') as f:
        f.write(output)
    i = i + 1
```

Now, the script will generate a new template instance for each iteration, and it will correctly render all unique combinations.


#### You:
didn't work

#### ChatGPT:
Apologies for the confusion. Upon further consideration, the issue might not be with the Jinja2 template object mutation. Jinja2 template objects are not mutable, and calling `render` should not change the state of the template.

The issue might be with how you're referencing the variables in the Jinja2 template file itself (`variant2.j2`).

The template context (the variables and their values) for the template should be a dictionary where the keys are the variable names and the values are their corresponding values. In your case, you're passing a dictionary `variant` which itself contains these values.

So, in your template, you should be checking for `{% if variant.hasWithdrawalLimit %}` instead of `{% if hasWithdrawalLimit %}`.

Try changing the context in the template file to use `variant` as a prefix, like so:

```jinja2
{% if variant.hasWithdrawalLimit %}
... do something ...
{% endif %}
```

If you don't want to change the template file, you could alternatively adjust the `render` function call to spread the dictionary values into the function instead of passing the entire dictionary:

```python
output = template.render(**combination)
```

This will pass the key-value pairs in the dictionary directly into the `render` function, which should properly set the context for the template rendering.

Here's your updated script:

```python
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
    template = env.get_template('variant2.j2')
    output = template.render(**combination)  # Unpack the dictionary
    with open(f'VariantImpr{str(i)}.java', 'w') as f:
        f.write(output)
    i = i + 1
```

This change will fix the issue you're seeing, provided your template file (`variant2.j2`) correctly uses the variables you're providing.


#### You:
great it's now working!

I now want to derive three specific variants, called VariantRevisit1, VariantRevisit2, VariantRevisit3... and corresponding to some specific feature variable value as documented here

Feature Variables	Variant1	Variant2	Variant3
hasWithdrawalLimit	Yes	Yes	No
supportsCurrency	No	Yes	Yes
isSingleAccount	No	Yes	No
hasConsortium	Yes	No	Yes

How to adapt the code?

#### ChatGPT:
To generate these three specific variants, you can define them directly and iterate over them, rather than generating all combinations. Here is the updated code to generate these specific variants:

```python
from jinja2 import Environment, FileSystemLoader

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
```

This code will generate three Java files: `VariantRevisit1.java`, `VariantRevisit2.java`, and `VariantRevisit3.java`, each corresponding to the specified feature variable values.
