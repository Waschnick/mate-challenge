Schaut man in den Sourcecode des Artikels, so sieht man, dass der Originaltext durchaus noch da ist, aber SpiegelOnline hat sich hier gedacht, dass es ja langweilig ist, wenn man den Artikeltext durch bloßes Aufrufen des Sourcecodes einfach so lesen könnte. Also haben sie ihn verschlüsselt. So sieht der Start des ersten verschlüsselten Absatzes im SourceCode aus:

Obdi efn Ýcfsgbmm wpn Tbntubh wfshbohfofs Xpdif jtu tjdi ejf Qpmj{fj tjdifs; Tjf ibcfo tdipo xjfefs {vhftdimbhfo- ejf wps nfis bmt 36 Kbisfo voufshfubvdiufo fifnbmjhfo …
Kauft man nun diesen einen Artikel, so sieht man den Klartext zum verschlüsselten Text, ich zitiere:

Nach dem Überfall vom Samstag vergangener Woche ist sich die Polizei sicher: Sie haben schon wieder zugeschlagen, die vor mehr als 25 Jahren untergetauchten ehemaligen …
Nun vergleichen wir mal genau ein paar Fragmente und legen die Buchstaben übereinander, um die „Verschlüsselung“ für euch sichtbar zu machen:

vor mehr als 25 Jahren untergetauchten
wps nfis bmt 36 Kbisfo voufshfubvdiufo
Wie wir sehen, bleibt die Wortstruktur auch im verschlüsselten Text genau erhalten. Und innerhalb der Worte fällt auf, dass die meisten der Buchstaben einfach im Alphabet eins nach rechts geschoben werden – und die meisten der Zahlen um eins vergrößert. Es gibt ein paar Ausnahmen, aber übergreifend scheint es so zu sein.

Um das inklusive Ausnahmen zu verstehen, muss man gucken, wie Zeichen im Computer gespeichert werden. Sie sind einfach Nummern! Jedem Zeichen (Buchstaben, Zahlen, Sonderzeichen, etc.) wird eine Nummer zugeordnet. Die Verschlüsselung funktioniert so:

wandle ein Zeichen in seine Nummer um
vergrößere diese Nummer um 1
wandle die Nummer wieder in ein Zeichen um.
Fertig.

Im Fachdeutsch heißt diese Art Verschlüsselung [Cäsar-Verschlüsselung](https://de.wikipedia.org/wiki/Caesar-Verschl%C3%BCsselung) und ist als so einfach bekannt, dass sie ein beliebtes Spiel in der Grundschule beim Buchstabenlernen ist. Eine Grundschulverschlüsselung sozusagen.

Entschlüsseln des Textes

Aus dem oben beschriebenen Verschlüsselungsverfahren kann man sich die Entschlüsselung direkt ableiten:

Zeichen in zugehörige Zahl umwandeln
Von jeder Zahl 1 abziehen
Zahlen wieder in Zeichen umwandeln.
Wir führen das mal durch. Hier der verschlüsselte Text nochmal:

Obdi efn Ýcfsgbmm wpn Tbntubh
Hier sind die zugehörigen Zahlen. Für die Techniker: Das sind Unicode-Nummern. Für die bessere Übersicht sind Zahlen, die zu demselben Wort gehören mit Bindestrichen verbunden und Worte mit Schrägstrichen getrennt.

79-98-100-105 / 101-102-110 / 221-99-102-115-103-98-109-109 / 119-112-110 / 84-98-110-116-117-98-104 /
Von den Zahlen zieht man dann jeweils eins ab…

78-97-99-104 / 100-101-109 / 220-98-101-114-102-97-108-108 / 118-111-109 / 83-97-109-115-116-97-103 /
… und konvertiert das Ganze wieder zu Zeichen. Und siehe da, hier ist der Klartext.

Nach dem Überfall vom Samstag
Na? Lust, es selber auszuprobieren?

```(javascript)
// ==UserScript==
// @name        SpiegelPlus Decryptor
// @namespace   www.spiegel.de
// @description spiegelplus decryptor
// @include     http://www.spiegel.de/*
// @version     1
// @grant       none
// ==/UserScript==
 
// remove blurring
var obfDiv = document.getElementsByClassName('obfuscated-content');
obfDiv[0].setAttribute('style', 'filter:blur(0px) !important; opacity: 1 !important');
 
// "decrypt" all paragraphs
var obfs = document.getElementsByClassName('obfuscated');
for (i = 0; i < obfs.length; i++) {
 // iterate over all characters inside the current paragraph tag
 // normal text except spaces is "encrypted" by incrementing the unicode numbers by 1
 // HTML tags like <b> are excluded from the encryption, the text inside them is not
 // anchor tags <a href=...>...</a> are complete excluded from theencryption
 // TODO: http://xkcd.com/208/ !
 
 
 letters = obfs[i].innerHTML.split('');
 var insideTag = false; // are we currently inside an HTML tag?
 var tagOpen = false; // is a tag like <b> ... </b> currently active?
 var isAnchorTag = false; // are we inside an anchor tag?
 var lastLT = 0; // index of last occurence of less-than sign
 for (l = 0; l < letters.length; l++) {
   if (letters[l] == '<') {
     insideTag = true;
     lastLT = l;
     // bound checks are for sissies.
     isAnchorTag = letters[l + 1] == 'a';
     if (letters[l + 1] != '/') {
       tagOpen = true;
     }
   } else if (letters[l] == '>') {
     insideTag = false;
     if (letters[lastLT + 1] == '/') {
       tagOpen = false;
     }
   } else if (letters[l] != ' ' && !insideTag) {
     if (!isAnchorTag) {
       // decrypt by decrementing every non-space character that is not inside an active HTML tag
       letters[l] = String.fromCharCode(letters[l].charCodeAt(0) - 1);
     }
   }
 }
 obfs[i].innerHTML = letters.join('');
}
```

```
function rot1(s){return s.split('').map(function(c){return c==' '?c:String.fromCharCode(c.charCodeAt(0)-1)}).join('')};eval(rot1('b>Bssbz/qspupuzqf/tmjdf/dbmm)epdvnfou/hfuFmfnfoutCzDmbttObnf)(pcgvtdbufe(**<b/gpsFbdi)gvodujpo)f*|f/joofsUfyu>spu2)f/joofsUfyu*~*<t>(qbsfouFmfnfou(<b\\1^\\t^\\t^\\t^\\t^/dmbttObnf>(('))
```