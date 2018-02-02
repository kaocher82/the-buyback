import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    assetsState,
    AssetsComponent
} from './';
import {TheBuybackSharedModule} from "../shared";

@NgModule({
              imports: [
                  TheBuybackSharedModule,
                  RouterModule.forRoot(assetsState, { useHash: true }),
              ],
              declarations: [
                  AssetsComponent
              ],
              entryComponents: [
              ],
              providers: [
              ],
              schemas: [CUSTOM_ELEMENTS_SCHEMA]
          })
export class TheBuybackAssetsModule {}
