from jinja2 import Template

template = Template('''
@startuml
!define DOUBLE double

class Account {
    - id: string
    - amount: DOUBLE
    - currency: int
    + Account(i: string, m: DOUBLE)
    + deposit(amount: DOUBLE)
    + getAmount(): DOUBLE
    + withdraw(amount: DOUBLE)
    {% if variant == 2 or variant == 3 %}
    + withdrawWithLimit(amount: DOUBLE)
    {% endif %}
    {% if variant == 3 %}
    + withdrawWithoutLimit(amount: DOUBLE)
    {% endif %}
    + getCurrency(): int
    + setCurrency(c: int)
}

class Bank {
    - accounts: Account[*]
    - converter: Converter
    {% if variant == 2 or variant == 3 %}
    - cons: Consortium
    {% endif %}
    + Bank(c: Converter)
    {% if variant == 3 %}
    + Bank(c: Consortium)
    {% endif %}
    + addClient(c: Client): string
    + depositOnAccount(id: string, amount: DOUBLE)
    + withdrawFromAccount(id: string, amount: DOUBLE)
    + convert(curSource: int, curTarget: int, amount: DOUBLE): DOUBLE
}

class Client {
    - id: string
    - name: string
    - accounts: Account[*]
}

class Converter {
    + conv(curSource: int, curTarget: int, amount: DOUBLE): DOUBLE
}

{% if variant == 2 or variant == 3 %}
class Consortium {
}
{% endif %}

Account -- Bank: "accounts" 
Client -- Bank: "clients" 
Bank -- Converter: "converter"
{% if variant == 2 or variant == 3 %}
Bank -- Consortium: "cons"
{% endif %}
@enduml
''')

variant1_plantuml = template.render(variant=1)
variant2_plantuml = template.render(variant=2)
variant3_plantuml = template.render(variant=3)

print("Variant 1 PlantUML:\n", variant1_plantuml)
print("Variant 2 PlantUML:\n", variant2_plantuml)
print("Variant 3 PlantUML:\n", variant3_plantuml)
