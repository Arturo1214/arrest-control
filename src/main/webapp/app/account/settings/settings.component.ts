import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiLanguageService } from 'ng-jhipster';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { LANGUAGES } from 'app/core/language/language.constants';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
  account!: Account;
  success = false;
  languages = LANGUAGES;
  settingsForm = this.fb.group({
    fullName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(250)]],
    telephone: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(250)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    langKey: [undefined]
  });

  constructor(private accountService: AccountService, private fb: FormBuilder, private languageService: JhiLanguageService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          fullName: account.fullName,
          telephone: account.telephone,
          email: account.email,
          langKey: account.langKey
        });

        this.account = account;
      }
    });
  }

  save(): void {
    this.success = false;

    this.account.fullName = this.settingsForm.get('fullName')!.value;
    this.account.telephone = this.settingsForm.get('telephone')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    this.account.langKey = this.settingsForm.get('langKey')!.value;

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);

      if (this.account.langKey !== this.languageService.getCurrentLanguage()) {
        this.languageService.changeLanguage(this.account.langKey);
      }
    });
  }
}
