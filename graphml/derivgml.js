const ejs = require('ejs');
const fs = require('fs');

const template = fs.readFileSync('./template.ejs', 'utf-8');

// For variant 1
const renderedVariant1 = ejs.render(template, { variant: 1 });
fs.writeFileSync('variant1.dot', renderedVariant1, 'utf8');

// For variant 2
const renderedVariant2 = ejs.render(template, { variant: 2 });
fs.writeFileSync('variant2.dot', renderedVariant2, 'utf8');

// For variant 3
const renderedVariant3 = ejs.render(template, { variant: 3 });
fs.writeFileSync('variant3.dot', renderedVariant3, 'utf8');

