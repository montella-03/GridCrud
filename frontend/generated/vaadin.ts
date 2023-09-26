import './vaadin-featureflags.js';

import './index';

import 'Frontend/generated/jar-resources/vaadin-dev-tools/vaadin-dev-tools.js';

import './theme-store.global.generated.js';
import { applyTheme } from './theme.js';
applyTheme(document);
