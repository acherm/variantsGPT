const ejs = require('ejs');
const fs = require('fs');

const template = fs.readFileSync('./template2.ejs', 'utf-8');

// Here are the feature configurations for the three original variants:
const variants = [
    { // Variant 1
      featureFindVillain: true,
      featureSmash: true,
      featureFireRocket: false,
      featureReload: false,
      featureThrowHammer: false,
      featureRetrieveWeapon: false,
      featureFinishHim: true,
      featureExtraNode: false,
    },
    { // Variant 2
      featureFindVillain: true,
      featureSmash: false,
      featureFireRocket: true,
      featureReload: true,
      featureThrowHammer: false,
      featureRetrieveWeapon: false,
      featureFinishHim: true,
      featureExtraNode: false,
    },
    { // Variant 3
      featureFindVillain: true,
      featureSmash: true,
      featureFireRocket: false,
      featureReload: false,
      featureThrowHammer: true,
      featureRetrieveWeapon: true,
      featureFinishHim: true,
      featureExtraNode: true,
    }
  ];
  
// Add 10 random variants:
for (let i = 0; i < 10; i++) {
    const extraNodeValue = Math.random() < 0.5;
    variants.push({
      featureFindVillain: true,  // always true
      featureSmash: Math.random() < 0.5,
      featureFireRocket: Math.random() < 0.5,
      featureReload: Math.random() < 0.5,
      featureThrowHammer: extraNodeValue,
      featureRetrieveWeapon: extraNodeValue,
      featureFinishHim: Math.random() < 0.5,
      featureExtraNode: extraNodeValue,
    });
  }
  // Render and print all variants:
  for (let i = 0; i < variants.length; i++) {
    const variant = variants[i];
    const rendered = ejs.render(template, variant);
    fs.writeFileSync(`variant-ft-${i + 1}.dot`, rendered, 'utf8');
  }

    

