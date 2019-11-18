define("ace/theme/vibrant_ink",["require","exports","module","ace/lib/dom"], function(require, exports, module) {

exports.isDark = true;
exports.cssClass = "ace-vibrjp-ink";
exports.cssText = ".ace-vibrjp-ink .ace_gutter {\
background: #1a1a1a;\
color: #BEBEBE\
}\
.ace-vibrjp-ink .ace_print-margin {\
width: 1px;\
background: #1a1a1a\
}\
.ace-vibrjp-ink {\
background-color: #0F0F0F;\
color: #FFFFFF\
}\
.ace-vibrjp-ink .ace_cursor {\
color: #FFFFFF\
}\
.ace-vibrjp-ink .ace_marker-layer .ace_selection {\
background: #6699CC\
}\
.ace-vibrjp-ink.ace_multiselect .ace_selection.ace_start {\
box-shadow: 0 0 3px 0px #0F0F0F;\
}\
.ace-vibrjp-ink .ace_marker-layer .ace_step {\
background: rgb(102, 82, 0)\
}\
.ace-vibrjp-ink .ace_marker-layer .ace_bracket {\
margin: -1px 0 0 -1px;\
border: 1px solid #404040\
}\
.ace-vibrjp-ink .ace_marker-layer .ace_active-line {\
background: #333333\
}\
.ace-vibrjp-ink .ace_gutter-active-line {\
background-color: #333333\
}\
.ace-vibrjp-ink .ace_marker-layer .ace_selected-word {\
border: 1px solid #6699CC\
}\
.ace-vibrjp-ink .ace_invisible {\
color: #404040\
}\
.ace-vibrjp-ink .ace_keyword,\
.ace-vibrjp-ink .ace_meta {\
color: #FF6600\
}\
.ace-vibrjp-ink .ace_constant,\
.ace-vibrjp-ink .ace_constant.ace_character,\
.ace-vibrjp-ink .ace_constant.ace_character.ace_escape,\
.ace-vibrjp-ink .ace_constant.ace_other {\
color: #339999\
}\
.ace-vibrjp-ink .ace_constant.ace_numeric {\
color: #99CC99\
}\
.ace-vibrjp-ink .ace_invalid,\
.ace-vibrjp-ink .ace_invalid.ace_deprecated {\
color: #CCFF33;\
background-color: #000000\
}\
.ace-vibrjp-ink .ace_fold {\
background-color: #FFCC00;\
border-color: #FFFFFF\
}\
.ace-vibrjp-ink .ace_entity.ace_name.ace_function,\
.ace-vibrjp-ink .ace_support.ace_function,\
.ace-vibrjp-ink .ace_variable {\
color: #FFCC00\
}\
.ace-vibrjp-ink .ace_variable.ace_parameter {\
font-style: italic\
}\
.ace-vibrjp-ink .ace_string {\
color: #66FF00\
}\
.ace-vibrjp-ink .ace_string.ace_regexp {\
color: #44B4CC\
}\
.ace-vibrjp-ink .ace_comment {\
color: #9933CC\
}\
.ace-vibrjp-ink .ace_entity.ace_other.ace_attribute-name {\
font-style: italic;\
color: #99CC99\
}\
.ace-vibrjp-ink .ace_indent-guide {\
background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAACCAYAAACZgbYnAAAAEklEQVQImWNgYGBgYNDTc/oPAALPAZ7hxlbYAAAAAElFTkSuQmCC) right repeat-y\
}";

var dom = require("../lib/dom");
dom.importCssString(exports.cssText, exports.cssClass);
});                (function() {
                    window.require(["ace/theme/vibrant_ink"], function(m) {
                        if (typeof module == "object" && typeof exports == "object" && module) {
                            module.exports = m;
                        }
                    });
                })();
            