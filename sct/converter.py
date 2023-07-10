import xml.etree.ElementTree as ET

# Load the XML document
tree = ET.parse('VendingMachine1.sct')
root = tree.getroot()

# Create the DOT string
dot = 'digraph statechart {\n'

# Extract states and transitions from XML
for state in root.findall('.//{http://www.yakindu.org/sct/sgraph/2.0.0}vertices[@xsi:type="sgraph:Entry"]'):
    print(state)
    state_id = state.get('{http://www.omg.org/XMI}id')
    state_name = state.get('name')
    dot += f'  {state_id} [label="{state_name}"];\n'

    # Extract outgoing transitions
    for transition in state.findall('.//{http://www.yakindu.org/sct/sgraph/2.0.0}outgoingTransitions'):
        transition_id = transition.get('{http://www.omg.org/XMI}id')
        target_id = transition.get('target').replace('_', '')
        specification = transition.get('specification')
        dot += f'  {state_id} -> {target_id} [label="{specification}"];\n'

# Add closing statement
dot += '}'

# Save the DOT string to a file
with open('output.dot', 'w') as f:
    f.write(dot)
